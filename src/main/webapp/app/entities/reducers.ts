import billItems from 'app/entities/bill-items/bill-items.reducer';
import bill from 'app/entities/bill/bill.reducer';
import menu from 'app/entities/menu/menu.reducer';
import hotel from 'app/entities/hotel/hotel.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  billItems,
  bill,
  menu,
  hotel,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
