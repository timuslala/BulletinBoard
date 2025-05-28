import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdListPageComponent } from './ad-list-page.component';

describe('AdListPageComponent', () => {
  let component: AdListPageComponent;
  let fixture: ComponentFixture<AdListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdListPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
