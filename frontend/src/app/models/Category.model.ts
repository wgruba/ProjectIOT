export interface Category {
    id: number;
    name: string;
    subcategories: { first: number; second: string; }[];
  }