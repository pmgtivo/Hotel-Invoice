import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './hotel.reducer';

export const Hotel = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const hotelList = useAppSelector(state => state.hotel.entities);
  const loading = useAppSelector(state => state.hotel.loading);

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
      <h2 id="hotel-heading" data-cy="HotelHeading">
        <Translate contentKey="hotelInvoiceApp.hotel.home.title">Hotels</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hotelInvoiceApp.hotel.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/hotel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hotelInvoiceApp.hotel.home.createLabel">Create new Hotel</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {hotelList && hotelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.address">Address</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('address')} />
                </th>
                <th className="hand" onClick={sort('contact')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.contact">Contact</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contact')} />
                </th>
                <th className="hand" onClick={sort('ownerName')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.ownerName">Owner Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('ownerName')} />
                </th>
                <th className="hand" onClick={sort('ownerContact')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.ownerContact">Owner Contact</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('ownerContact')} />
                </th>
                <th className="hand" onClick={sort('gstNo')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.gstNo">Gst No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('gstNo')} />
                </th>
                <th className="hand" onClick={sort('isVeg')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.isVeg">Is Veg</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isVeg')} />
                </th>
                <th className="hand" onClick={sort('logo')}>
                  <Translate contentKey="hotelInvoiceApp.hotel.logo">Logo</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('logo')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {hotelList.map((hotel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/hotel/${hotel.id}`} color="link" size="sm">
                      {hotel.id}
                    </Button>
                  </td>
                  <td>{hotel.name}</td>
                  <td>{hotel.address}</td>
                  <td>{hotel.contact}</td>
                  <td>{hotel.ownerName}</td>
                  <td>{hotel.ownerContact}</td>
                  <td>{hotel.gstNo}</td>
                  <td>{hotel.isVeg ? 'true' : 'false'}</td>
                  <td>
                    {hotel.logo ? (
                      <div>
                        {hotel.logoContentType ? (
                          <a onClick={openFile(hotel.logoContentType, hotel.logo)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {hotel.logoContentType}, {byteSize(hotel.logo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/hotel/${hotel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/hotel/${hotel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/hotel/${hotel.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hotelInvoiceApp.hotel.home.notFound">No Hotels found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Hotel;
