import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBill } from 'app/shared/model/bill.model';
import { getEntities as getBills } from 'app/entities/bill/bill.reducer';
import { IBillItems } from 'app/shared/model/bill-items.model';
import { getEntity, updateEntity, createEntity, reset } from './bill-items.reducer';

export const BillItemsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bills = useAppSelector(state => state.bill.entities);
  const billItemsEntity = useAppSelector(state => state.billItems.entity);
  const loading = useAppSelector(state => state.billItems.loading);
  const updating = useAppSelector(state => state.billItems.updating);
  const updateSuccess = useAppSelector(state => state.billItems.updateSuccess);

  const handleClose = () => {
    navigate('/bill-items');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBills({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...billItemsEntity,
      ...values,
      bill: bills.find(it => it.id.toString() === values.bill.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...billItemsEntity,
          bill: billItemsEntity?.bill?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hotelInvoiceApp.billItems.home.createOrEditLabel" data-cy="BillItemsCreateUpdateHeading">
            <Translate contentKey="hotelInvoiceApp.billItems.home.createOrEditLabel">Create or edit a BillItems</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="bill-items-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Item Count" id="bill-items-itemCount" name="itemCount" data-cy="itemCount" type="text" />
              <ValidatedField label="Amount" id="bill-items-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField id="bill-items-bill" name="bill" data-cy="bill" label="Bill" type="select">
                <option value="" key="0" />
                {bills
                  ? bills.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bill-items" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BillItemsUpdate;
