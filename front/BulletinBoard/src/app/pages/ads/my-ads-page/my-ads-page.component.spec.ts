import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAdsPageComponent } from './my-ads-page.component';

describe('MyAdsPageComponent', () => {
  let component: MyAdsPageComponent;
  let fixture: ComponentFixture<MyAdsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyAdsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyAdsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
