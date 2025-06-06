import { Component, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../../../services/messages.service';
import { Conversation } from '../../../models/message.model';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-message-conversation',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './message-conversation.component.html',
  styleUrls: ['./message-conversation.component.scss'],
})
export class MessageConversationComponent {
  private route = inject(ActivatedRoute);
  private messageService = inject(MessageService);

  conversation = signal<Conversation | null>(null);

  constructor() {
    effect(() => {
      const id = Number(this.route.snapshot.paramMap.get('id'));
      this.messageService.getConversationById(id).subscribe((data) => this.conversation.set(data));
    });
  }
}
