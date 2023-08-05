import { IMenu } from 'app/shared/model/menu.model';
import { IBill } from 'app/shared/model/bill.model';

export interface IHotel {
  id?: number;
  name?: string;
  address?: string | null;
  contact?: string | null;
  ownerName?: string | null;
  ownerContact?: string | null;
  gstNo?: string | null;
  isVeg?: boolean | null;
  logoContentType?: string | null;
  logo?: string | null;
  menus?: IMenu[] | null;
  bills?: IBill[] | null;
}

export const defaultValue: Readonly<IHotel> = {
  isVeg: false,
};
