import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './bill.reducer';

export const Bill = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const billList = useAppSelector(state => state.bill.entities);
  const loading = useAppSelector(state => state.bill.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="bill-heading" data-cy="BillHeading">
        <Translate contentKey="hotelInvoiceApp.bill.home.title">Bills</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hotelInvoiceApp.bill.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/bill/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hotelInvoiceApp.bill.home.createLabel">Create new Bill</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {billList && billList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="hotelInvoiceApp.bill.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('tableNo')}>
                  <Translate contentKey="hotelInvoiceApp.bill.tableNo">Table No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('tableNo')} />
                </th>
                <th className="hand" onClick={sort('customerName')}>
                  <Translate contentKey="hotelInvoiceApp.bill.customerName">Customer Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('customerName')} />
                </th>
                <th className="hand" onClick={sort('customerContact')}>
                  <Translate contentKey="hotelInvoiceApp.bill.customerContact">Customer Contact</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('customerContact')} />
                </th>
                <th className="hand" onClick={sort('cgst')}>
                  <Translate contentKey="hotelInvoiceApp.bill.cgst">Cgst</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cgst')} />
                </th>
                <th className="hand" onClick={sort('sgst')}>
                  <Translate contentKey="hotelInvoiceApp.bill.sgst">Sgst</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('sgst')} />
                </th>
                <th className="hand" onClick={sort('totalAmount')}>
                  <Translate contentKey="hotelInvoiceApp.bill.totalAmount">Total Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalAmount')} />
                </th>
                <th className="hand" onClick={sort('isParcel')}>
                  <Translate contentKey="hotelInvoiceApp.bill.isParcel">Is Parcel</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isParcel')} />
                </th>
                <th className="hand" onClick={sort('parcelCharges')}>
                  <Translate contentKey="hotelInvoiceApp.bill.parcelCharges">Parcel Charges</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('parcelCharges')} />
                </th>
                <th className="hand" onClick={sort('adjustAmount')}>
                  <Translate contentKey="hotelInvoiceApp.bill.adjustAmount">Adjust Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('adjustAmount')} />
                </th>
                <th className="hand" onClick={sort('discountPercentage')}>
                  <Translate contentKey="hotelInvoiceApp.bill.discountPercentage">Discount Percentage</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('discountPercentage')} />
                </th>
                <th className="hand" onClick={sort('paidBy')}>
                  <Translate contentKey="hotelInvoiceApp.bill.paidBy">Paid By</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('paidBy')} />
                </th>
                <th className="hand" onClick={sort('createDateTime')}>
                  <Translate contentKey="hotelInvoiceApp.bill.createDateTime">Create Date Time</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createDateTime')} />
                </th>
                <th>
                  <Translate contentKey="hotelInvoiceApp.bill.hotel">Hotel</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {billList.map((bill, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bill/${bill.id}`} color="link" size="sm">
                      {bill.id}
                    </Button>
                  </td>
                  <td>{bill.tableNo}</td>
                  <td>{bill.customerName}</td>
                  <td>{bill.customerContact}</td>
                  <td>{bill.cgst}</td>
                  <td>{bill.sgst}</td>
                  <td>{bill.totalAmount}</td>
                  <td>{bill.isParcel ? 'true' : 'false'}</td>
                  <td>{bill.parcelCharges}</td>
                  <td>{bill.adjustAmount}</td>
                  <td>{bill.discountPercentage}</td>
                  <td>{bill.paidBy}</td>
                  <td>{bill.createDateTime ? <TextFormat type="date" value={bill.createDateTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{bill.hotel ? <Link to={`/hotel/${bill.hotel.id}`}>{bill.hotel.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/bill/${bill.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/bill/${bill.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/bill/${bill.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hotelInvoiceApp.bill.home.notFound">No Bills found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Bill;
