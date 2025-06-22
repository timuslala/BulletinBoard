import { inject, Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Conversation, Message } from '../models/message.model';
import { TokenService } from './token.service';

@Injectable({ providedIn: 'root' })
export class MessageService {
  private tokenService = inject(TokenService);

  private mockConversations: Conversation[] = [
    {
      id: 1,
      name: 'Jan Kowalski',
      lastMessage: 'Cześć! Co słychać?',
      messages: [
        {
          id: 1,
          conversationId: 1,
          sender: 'Jan',
          text: 'Cześć! Co słychać?',
          timestamp: '2025-05-28T10:00:00Z',
        },
        {
          id: 2,
          conversationId: 1,
          sender: 'Ty',
          text: 'Hej, dobrze! A Ty?',
          timestamp: '2025-05-28T10:01:00Z',
        },
      ],
    },
    {
      id: 2,
      name: 'Anna Nowak',
      lastMessage: 'Do zobaczenia jutro!',
      messages: [
        {
          id: 3,
          conversationId: 2,
          sender: 'Anna',
          text: 'Do zobaczenia jutro!',
          timestamp: '2025-05-27T12:00:00Z',
        },
      ],
    },
  ];

  getAllConversations(): Observable<Conversation[]> {
    return of(this.mockConversations);
  }

  getConversationById(id: number): Observable<Conversation | null> {
    const conversation = this.mockConversations.find((c) => c.id === id);
    return of(conversation || null);
  }

  sendMessageToSeller(adId: number): Observable<any> {
    const headers = this.tokenService.getAuthHeaders();
    console.log(`MOCK sendMessageToSeller: ogłoszenie o ID ${adId}`);
    return of({ status: 'message_sent', adId });
  }
}
