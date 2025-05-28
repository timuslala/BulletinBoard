import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdDetailPageComponent } from './ad-detail-page.component';

describe('AdDetailPageComponent', () => {
  let component: AdDetailPageComponent;
  let fixture: ComponentFixture<AdDetailPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdDetailPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
