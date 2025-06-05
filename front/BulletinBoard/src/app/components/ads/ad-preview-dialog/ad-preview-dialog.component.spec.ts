import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdPreviewDialogComponent } from './ad-preview-dialog.component';

describe('AdPreviewDialogComponent', () => {
  let component: AdPreviewDialogComponent;
  let fixture: ComponentFixture<AdPreviewDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdPreviewDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdPreviewDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
