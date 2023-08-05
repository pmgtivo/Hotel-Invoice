import dayjs from 'dayjs';
import { IBillItems } from 'app/shared/model/bill-items.model';
import { IHotel } from 'app/shared/model/hotel.model';

export interface IBill {
  id?: number;
  tableNo?: string | null;
  customerName?: string | null;
  customerContact?: string | null;
  cgst?: string | null;
  sgst?: string | null;
  totalAmount?: number;
  isParcel?: boolean | null;
  parcelCharges?: number | null;
  adjustAmount?: number | null;
  discountPercentage?: number | null;
  paidBy?: string | null;
  createDateTime?: string | null;
  items?: IBillItems[] | null;
  hotel?: IHotel | null;
}

export const defaultValue: Readonly<IBill> = {
  isParcel: false,
};
