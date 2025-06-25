import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdEditPageComponent } from './ad-edit-page.component';

describe('AdEditPageComponent', () => {
  let component: AdEditPageComponent;
  let fixture: ComponentFixture<AdEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdEditPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
