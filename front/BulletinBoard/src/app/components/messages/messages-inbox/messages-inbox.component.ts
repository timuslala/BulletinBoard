import { Component, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { MessageService } from '../../../services/messages.service';
import { Conversation } from '../../../models/message.model';

@Component({
  selector: 'app-messages-inbox',
  standalone: true,
  imports: [CommonModule, MatListModule, MatIconModule, RouterModule],
  templateUrl: './messages-inbox.component.html',
  styleUrls: ['./messages-inbox.component.scss'],
})
export class MessagesInboxComponent {
  private messageService = inject(MessageService);

  conversations = signal<Conversation[]>([]);

  constructor() {
    effect(() => {
      this.messageService.getAllConversations().subscribe((data) => this.conversations.set(data));
    });
  }
}
