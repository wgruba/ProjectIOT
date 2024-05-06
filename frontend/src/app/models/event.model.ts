export interface Event {
  id: number;
  name: string;
  organizer: number;
  categoryList: number[];
  clientList: [];
  description: string;
  size: number;
  localisation: string;
  isFree: boolean;
  isReservationNecessary: boolean;
  ageGroup: string; 
  startDate: string;
  endDate: string;
  eventStatus: string;
  imageUrl: string;
}