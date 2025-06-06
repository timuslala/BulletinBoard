export interface Conversation {
  id: number;
  name: string;
  lastMessage: string;
  messages: Message[];
}

export interface Message {
  id: number;
  conversationId: number;
  sender: string;
  text: string;
  timestamp: string;
}
