export interface Ad {
  id?: number;
  title: string;
  description: string;
  tags: string[];
  showEmail: boolean;
  showPhone: boolean;
  images?: File[]; 
}