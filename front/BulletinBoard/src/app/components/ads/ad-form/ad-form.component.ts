import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatOptionModule } from '@angular/material/core';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';

import { TagsService } from '../../../services/tags.service';
import { AdPreviewDialogComponent } from '../ad-preview-dialog/ad-preview-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { TagUsage } from '../../../models/tag.model';
import { Observable, of } from 'rxjs';
import {
  debounceTime,
  distinctUntilChanged,
  switchMap,
  map,
  tap,
} from 'rxjs/operators';

@Component({
  selector: 'app-ad-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    MatOptionModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatCheckboxModule,
    MatDialogModule,
  ],
  templateUrl: './ad-form.component.html',
  styleUrls: ['./ad-form.component.scss'],
})
export class AdFormComponent implements OnInit {
  @Output() submitted = new EventEmitter<any>();

  form: FormGroup;

  tagSuggestions: TagUsage[] = [];
  isLoadingTags = false;

  constructor(
    private fb: FormBuilder,
    private tagsService: TagsService,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(5)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      tags: [[], [Validators.required, Validators.minLength(1)]],
      newTag: [''],
      tagSearch: [''], // Now used for search
      images: this.fb.array([]),
      newImageUrl: [
        '',
        [Validators.pattern(/^https?:\/\/.+\.(jpg|jpeg|png|gif|webp)$/i)],
      ],
      showEmail: [false],
      showPhone: [false],
    });
  }

  ngOnInit() {
    this.form
      .get('tagSearch')
      ?.valueChanges.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => (this.isLoadingTags = true)),
        switchMap((prefix) =>
          prefix && prefix.trim().length > 0
            ? this.tagsService.suggestTags(prefix.trim())
            : of([])
        )
      )
      .subscribe((tags) => {
        this.tagSuggestions = tags.sort((a, b) => b.usageCount - a.usageCount);
        this.isLoadingTags = false;
      });
  }

  get images(): FormArray {
    return this.form.get('images') as FormArray;
  }

  submit() {
    if (this.form.valid) {
      const finalData = {
        ...this.form.value,
        tags: Array.from(new Set(this.form.value.tags)), // remove any duplicates
      };
      this.submitted.emit(finalData);
    } else {
      this.form.markAllAsTouched();
    }
  }

  addNewTag() {
    const newTag = this.form.value.newTag?.trim();
    if (newTag) {
      const tags = this.form.value.tags || [];
      if (!tags.includes(newTag)) {
        tags.push(newTag);
        this.form.patchValue({ tags, newTag: '', tagSearch: '' });
      }
    }
  }

  selectSuggestedTag(tagName: string) {
    const tags = this.form.value.tags || [];
    if (!tags.includes(tagName)) {
      tags.push(tagName);
      this.form.patchValue({ tags, tagSearch: '' }); // Reset search
    }
  }

  removeTag(tag: string) {
    const tags = this.form.value.tags.filter((t: string) => t !== tag);
    this.form.patchValue({ tags });
  }

  addNewImage() {
    const newImageUrl = this.form.value.newImageUrl.trim();
    if (!newImageUrl || !this.form.get('newImageUrl')?.valid) {
      return;
    }

    if (this.images.length >= 5) {
      this.form.get('newImageUrl')?.setErrors({ maxImages: true });
      return;
    }

    const isDuplicate = this.images.controls.some(
      (ctrl) => ctrl.value === newImageUrl
    );
    if (isDuplicate) {
      this.form.get('newImageUrl')?.setErrors({ duplicateImage: true });
      return;
    }

    this.images.push(
      new FormControl(newImageUrl, [
        Validators.pattern(/^https?:\/\/.+\.(jpg|jpeg|png|gif|webp)$/i),
      ])
    );
    this.form.patchValue({ newImageUrl: '' });
  }

  removeImage(index: number) {
    this.images.removeAt(index);
  }

  openPreview() {
    this.dialog.open(AdPreviewDialogComponent, {
      data: this.form.value,
      width: '900px',
      maxWidth: '900px',
    });
  }
}
