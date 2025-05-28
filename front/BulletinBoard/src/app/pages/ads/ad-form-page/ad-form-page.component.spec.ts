import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdFormPageComponent } from './ad-form-page.component';

describe('AdFormPageComponent', () => {
  let component: AdFormPageComponent;
  let fixture: ComponentFixture<AdFormPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdFormPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdFormPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
