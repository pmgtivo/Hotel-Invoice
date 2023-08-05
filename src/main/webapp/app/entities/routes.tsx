import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BillItems from './bill-items';
import Bill from './bill';
import Menu from './menu';
import Hotel from './hotel';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="bill-items/*" element={<BillItems />} />
        <Route path="bill/*" element={<Bill />} />
        <Route path="menu/*" element={<Menu />} />
        <Route path="hotel/*" element={<Hotel />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
