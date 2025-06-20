import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { TagsService } from '../../../services/tags.service';
import { AdPreviewDialogComponent } from '../ad-preview-dialog/ad-preview-dialog.component';

@Component({
  selector: 'app-ad-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatDialogModule,
  ],
  templateUrl: './ad-form.component.html',
  styleUrls: ['./ad-form.component.scss'],
})
export class AdFormComponent {
  @Output() submitted = new EventEmitter<any>();

  form: FormGroup;
  availableTags: string[] = [];

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
      images: this.fb.array([]), // <-- FormArray
      newImageUrl: [
        '',
        [Validators.pattern(/^https?:\/\/.+\.(jpg|jpeg|png|gif|webp)$/i)],
      ],
      showEmail: [false],
      showPhone: [false],
    });

    this.availableTags = this.tagsService.getTags(); // mock
  }

  submit() {
    if (this.form.valid) {
      this.submitted.emit(this.form.value);
    } else {
      this.form.markAllAsTouched();
    }
  }

  addNewTag() {
    const newTag = this.form.value.newTag.trim();
    if (newTag && !this.availableTags.includes(newTag)) {
      this.availableTags.push(newTag);
      const tags = this.form.value.tags;
      tags.push(newTag);
      this.form.patchValue({ tags, newTag: '' });
      this.tagsService.addTag(newTag); // tylko do mocków
    }
  }

  removeTag(tag: string) {
    const tags = this.form.value.tags.filter((t: string) => t !== tag);
    this.form.patchValue({ tags });
  }

  get images(): FormArray {
    return this.form.get('images') as FormArray;
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
