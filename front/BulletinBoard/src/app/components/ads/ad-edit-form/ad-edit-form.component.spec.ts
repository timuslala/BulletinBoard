import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdEditFormComponent } from './ad-edit-form.component';

describe('AdEditFormComponent', () => {
  let component: AdEditFormComponent;
  let fixture: ComponentFixture<AdEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdEditFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
