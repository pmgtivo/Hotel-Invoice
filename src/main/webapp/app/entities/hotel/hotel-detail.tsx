import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hotel.reducer';

export const HotelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hotelEntity = useAppSelector(state => state.hotel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hotelDetailsHeading">
          <Translate contentKey="hotelInvoiceApp.hotel.detail.title">Hotel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hotelEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hotelInvoiceApp.hotel.name">Name</Translate>
            </span>
          </dt>
          <dd>{hotelEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="hotelInvoiceApp.hotel.address">Address</Translate>
            </span>
          </dt>
          <dd>{hotelEntity.address}</dd>
          <dt>
            <span id="contact">
              <Translate contentKey="hotelInvoiceApp.hotel.contact">Contact</Translate>
            </span>
          </dt>
          <dd>{hotelEntity.contact}</dd>
          <dt>
            <span id="ownerName">
              <Translate contentKey="hotelInvoiceApp.hotel.ownerName">Owner Name</Translate>
            </span>
          </dt>
          <dd>{hotelEntity.ownerName}</dd>
          <dt>
            <span id="ownerContact">
              <Translate contentKey="hotelInvoiceApp.hotel.ownerContact">Owner Contact</Translate>
            </span>
          </dt>
          <dd>{hotelEntity.ownerContact}</dd>
          <dt>
            <span id="gstNo">
              <Translate contentKey="hotelInvoiceApp.hotel.gstNo">Gst No</Translate>
            </span>
          </dt>
          <dd>{hotelEntity.gstNo}</dd>
          <dt>
            <span id="isVeg">
              <Translate contentKey="hotelInvoiceApp.hotel.isVeg">Is Veg</Translate>
            </span>
          </dt>
          <dd>{hotelEntity.isVeg ? 'true' : 'false'}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="hotelInvoiceApp.hotel.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {hotelEntity.logo ? (
              <div>
                {hotelEntity.logoContentType ? (
                  <a onClick={openFile(hotelEntity.logoContentType, hotelEntity.logo)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {hotelEntity.logoContentType}, {byteSize(hotelEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/hotel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hotel/${hotelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HotelDetail;
