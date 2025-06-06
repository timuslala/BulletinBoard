import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessagesConversationComponent } from './messages-conversation.component';

describe('MessagesConversationComponent', () => {
  let component: MessagesConversationComponent;
  let fixture: ComponentFixture<MessagesConversationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MessagesConversationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MessagesConversationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
