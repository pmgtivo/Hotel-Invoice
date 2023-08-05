import { IBill } from 'app/shared/model/bill.model';

export interface IBillItems {
  id?: number;
  itemCount?: number | null;
  amount?: number | null;
  bill?: IBill | null;
}

export const defaultValue: Readonly<IBillItems> = {};
