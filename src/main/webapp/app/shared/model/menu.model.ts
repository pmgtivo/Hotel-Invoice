import { IHotel } from 'app/shared/model/hotel.model';

export interface IMenu {
  id?: number;
  name?: string;
  price?: string;
  description?: string | null;
  isVeg?: boolean | null;
  itemAvailable?: boolean | null;
  hotel?: IHotel | null;
}

export const defaultValue: Readonly<IMenu> = {
  isVeg: false,
  itemAvailable: false,
};
