import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { TagsService } from '../../../services/tags.service';
import {
  debounceTime,
  distinctUntilChanged,
  switchMap,
  tap,
} from 'rxjs/operators';
import { of } from 'rxjs';
import { TagUsage } from '../../../models/tag.model';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatOptionModule } from '@angular/material/core';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatAutocompleteModule,
    MatOptionModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
})
export class SearchBarComponent implements OnInit {
  @Output() search = new EventEmitter<{ query: string; tags: string[] }>();

  form: FormGroup;
  tagSuggestions: TagUsage[] = [];
  isLoadingTags = false;

  constructor(private fb: FormBuilder, private tagsService: TagsService) {
    this.form = this.fb.group({
      query: [''],
      tagSearch: [''],
      tags: [[]],
    });
  }

  ngOnInit() {
    this.form
      .get('tagSearch')
      ?.valueChanges.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => (this.isLoadingTags = true)),
        switchMap((value) =>
          value && value.trim().length > 0
            ? this.tagsService.suggestTags(value.trim())
            : of([])
        )
      )
      .subscribe((tags) => {
        this.tagSuggestions = tags.sort((a, b) => b.usageCount - a.usageCount);
        this.isLoadingTags = false;
      });
  }

  addTag(tag: string) {
    const tags = this.form.value.tags || [];
    if (!tags.includes(tag)) {
      tags.push(tag);
      this.form.patchValue({ tags, tagSearch: '' });
    }
  }

  removeTag(tag: string) {
    const tags = this.form.value.tags.filter((t: string) => t !== tag);
    this.form.patchValue({ tags });
  }

  submitSearch() {
    this.search.emit({
      query: this.form.value.query,
      tags: this.form.value.tags,
    });
  }
}
