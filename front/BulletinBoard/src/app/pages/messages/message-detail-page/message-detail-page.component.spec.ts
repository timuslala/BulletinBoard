import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageDetailPageComponent } from './message-detail-page.component';

describe('MessageDetailPageComponent', () => {
  let component: MessageDetailPageComponent;
  let fixture: ComponentFixture<MessageDetailPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MessageDetailPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MessageDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
