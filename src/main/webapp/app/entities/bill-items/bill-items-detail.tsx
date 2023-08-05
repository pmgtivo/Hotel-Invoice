import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bill-items.reducer';

export const BillItemsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const billItemsEntity = useAppSelector(state => state.billItems.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="billItemsDetailsHeading">
          <Translate contentKey="hotelInvoiceApp.billItems.detail.title">BillItems</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{billItemsEntity.id}</dd>
          <dt>
            <span id="itemCount">
              <Translate contentKey="hotelInvoiceApp.billItems.itemCount">Item Count</Translate>
            </span>
          </dt>
          <dd>{billItemsEntity.itemCount}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="hotelInvoiceApp.billItems.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{billItemsEntity.amount}</dd>
          <dt>
            <Translate contentKey="hotelInvoiceApp.billItems.bill">Bill</Translate>
          </dt>
          <dd>{billItemsEntity.bill ? billItemsEntity.bill.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bill-items" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bill-items/${billItemsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BillItemsDetail;
