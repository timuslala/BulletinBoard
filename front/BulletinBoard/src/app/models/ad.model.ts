export enum AdStatus {
  DRAFT = 'DRAFT',
  PUBLISHED = 'PUBLISHED',
  ARCHIVED = 'ARCHIVED',
}

export interface Ad {
  id: number;
  title: string;
  description: string;
  tags: string[];
  showEmail: boolean;
  showPhone: boolean;
  email?: string;
  phone?: number;
  images?: string[];
  status: AdStatus;
  sellerId?: number;
}
