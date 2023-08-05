import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BillItems from './bill-items';
import BillItemsDetail from './bill-items-detail';
import BillItemsUpdate from './bill-items-update';
import BillItemsDeleteDialog from './bill-items-delete-dialog';

const BillItemsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BillItems />} />
    <Route path="new" element={<BillItemsUpdate />} />
    <Route path=":id">
      <Route index element={<BillItemsDetail />} />
      <Route path="edit" element={<BillItemsUpdate />} />
      <Route path="delete" element={<BillItemsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BillItemsRoutes;
