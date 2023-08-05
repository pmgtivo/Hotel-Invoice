import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bill.reducer';

export const BillDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const billEntity = useAppSelector(state => state.bill.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="billDetailsHeading">
          <Translate contentKey="hotelInvoiceApp.bill.detail.title">Bill</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{billEntity.id}</dd>
          <dt>
            <span id="tableNo">
              <Translate contentKey="hotelInvoiceApp.bill.tableNo">Table No</Translate>
            </span>
          </dt>
          <dd>{billEntity.tableNo}</dd>
          <dt>
            <span id="customerName">
              <Translate contentKey="hotelInvoiceApp.bill.customerName">Customer Name</Translate>
            </span>
          </dt>
          <dd>{billEntity.customerName}</dd>
          <dt>
            <span id="customerContact">
              <Translate contentKey="hotelInvoiceApp.bill.customerContact">Customer Contact</Translate>
            </span>
          </dt>
          <dd>{billEntity.customerContact}</dd>
          <dt>
            <span id="cgst">
              <Translate contentKey="hotelInvoiceApp.bill.cgst">Cgst</Translate>
            </span>
          </dt>
          <dd>{billEntity.cgst}</dd>
          <dt>
            <span id="sgst">
              <Translate contentKey="hotelInvoiceApp.bill.sgst">Sgst</Translate>
            </span>
          </dt>
          <dd>{billEntity.sgst}</dd>
          <dt>
            <span id="totalAmount">
              <Translate contentKey="hotelInvoiceApp.bill.totalAmount">Total Amount</Translate>
            </span>
          </dt>
          <dd>{billEntity.totalAmount}</dd>
          <dt>
            <span id="isParcel">
              <Translate contentKey="hotelInvoiceApp.bill.isParcel">Is Parcel</Translate>
            </span>
          </dt>
          <dd>{billEntity.isParcel ? 'true' : 'false'}</dd>
          <dt>
            <span id="parcelCharges">
              <Translate contentKey="hotelInvoiceApp.bill.parcelCharges">Parcel Charges</Translate>
            </span>
          </dt>
          <dd>{billEntity.parcelCharges}</dd>
          <dt>
            <span id="adjustAmount">
              <Translate contentKey="hotelInvoiceApp.bill.adjustAmount">Adjust Amount</Translate>
            </span>
          </dt>
          <dd>{billEntity.adjustAmount}</dd>
          <dt>
            <span id="discountPercentage">
              <Translate contentKey="hotelInvoiceApp.bill.discountPercentage">Discount Percentage</Translate>
            </span>
          </dt>
          <dd>{billEntity.discountPercentage}</dd>
          <dt>
            <span id="paidBy">
              <Translate contentKey="hotelInvoiceApp.bill.paidBy">Paid By</Translate>
            </span>
          </dt>
          <dd>{billEntity.paidBy}</dd>
          <dt>
            <span id="createDateTime">
              <Translate contentKey="hotelInvoiceApp.bill.createDateTime">Create Date Time</Translate>
            </span>
          </dt>
          <dd>
            {billEntity.createDateTime ? <TextFormat value={billEntity.createDateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="hotelInvoiceApp.bill.hotel">Hotel</Translate>
          </dt>
          <dd>{billEntity.hotel ? billEntity.hotel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bill" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bill/${billEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BillDetail;
