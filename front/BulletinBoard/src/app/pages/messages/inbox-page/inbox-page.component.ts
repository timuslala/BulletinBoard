import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessagesInboxComponent } from '../../../components/messages/messages-inbox/messages-inbox.component';

@Component({
  selector: 'app-inbox-page',
  standalone: true,
  imports: [CommonModule, MessagesInboxComponent],
  templateUrl: './inbox-page.component.html',
  styleUrls: ['./inbox-page.component.scss'],
})
export class InboxPageComponent {}
