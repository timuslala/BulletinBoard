import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageConversationComponent } from '../../../components/messages/message-conversation/message-conversation.component';

@Component({
  selector: 'app-messages-conversation-page',
  standalone: true,
  imports: [CommonModule, MessageConversationComponent],
  templateUrl: './messages-conversation.component.html',
  styleUrls: ['./messages-conversation.component.scss'],
})
export class MessagesConversationPageComponent {}
