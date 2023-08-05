import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './bill-items.reducer';

export const BillItems = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const billItemsList = useAppSelector(state => state.billItems.entities);
  const loading = useAppSelector(state => state.billItems.loading);

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
      <h2 id="bill-items-heading" data-cy="BillItemsHeading">
        <Translate contentKey="hotelInvoiceApp.billItems.home.title">Bill Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hotelInvoiceApp.billItems.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/bill-items/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hotelInvoiceApp.billItems.home.createLabel">Create new Bill Items</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {billItemsList && billItemsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="hotelInvoiceApp.billItems.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('itemCount')}>
                  <Translate contentKey="hotelInvoiceApp.billItems.itemCount">Item Count</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('itemCount')} />
                </th>
                <th className="hand" onClick={sort('amount')}>
                  <Translate contentKey="hotelInvoiceApp.billItems.amount">Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('amount')} />
                </th>
                <th>
                  <Translate contentKey="hotelInvoiceApp.billItems.bill">Bill</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {billItemsList.map((billItems, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bill-items/${billItems.id}`} color="link" size="sm">
                      {billItems.id}
                    </Button>
                  </td>
                  <td>{billItems.itemCount}</td>
                  <td>{billItems.amount}</td>
                  <td>{billItems.bill ? <Link to={`/bill/${billItems.bill.id}`}>{billItems.bill.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/bill-items/${billItems.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/bill-items/${billItems.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/bill-items/${billItems.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hotelInvoiceApp.billItems.home.notFound">No Bill Items found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BillItems;
