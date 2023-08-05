import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHotel } from 'app/shared/model/hotel.model';
import { getEntities as getHotels } from 'app/entities/hotel/hotel.reducer';
import { IBill } from 'app/shared/model/bill.model';
import { getEntity, updateEntity, createEntity, reset } from './bill.reducer';

export const BillUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hotels = useAppSelector(state => state.hotel.entities);
  const billEntity = useAppSelector(state => state.bill.entity);
  const loading = useAppSelector(state => state.bill.loading);
  const updating = useAppSelector(state => state.bill.updating);
  const updateSuccess = useAppSelector(state => state.bill.updateSuccess);

  const handleClose = () => {
    navigate('/bill');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHotels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createDateTime = convertDateTimeToServer(values.createDateTime);

    const entity = {
      ...billEntity,
      ...values,
      hotel: hotels.find(it => it.id.toString() === values.hotel.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createDateTime: displayDefaultDateTime(),
        }
      : {
          ...billEntity,
          createDateTime: convertDateTimeFromServer(billEntity.createDateTime),
          hotel: billEntity?.hotel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hotelInvoiceApp.bill.home.createOrEditLabel" data-cy="BillCreateUpdateHeading">
            <Translate contentKey="hotelInvoiceApp.bill.home.createOrEditLabel">Create or edit a Bill</Translate>
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
                  id="bill-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Table No" id="bill-tableNo" name="tableNo" data-cy="tableNo" type="text" />
              <ValidatedField label="Customer Name" id="bill-customerName" name="customerName" data-cy="customerName" type="text" />
              <ValidatedField
                label="Customer Contact"
                id="bill-customerContact"
                name="customerContact"
                data-cy="customerContact"
                type="text"
              />
              <ValidatedField label="Cgst" id="bill-cgst" name="cgst" data-cy="cgst" type="text" />
              <ValidatedField label="Sgst" id="bill-sgst" name="sgst" data-cy="sgst" type="text" />
              <ValidatedField
                label="Total Amount"
                id="bill-totalAmount"
                name="totalAmount"
                data-cy="totalAmount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label="Is Parcel" id="bill-isParcel" name="isParcel" data-cy="isParcel" check type="checkbox" />
              <ValidatedField label="Parcel Charges" id="bill-parcelCharges" name="parcelCharges" data-cy="parcelCharges" type="text" />
              <ValidatedField label="Adjust Amount" id="bill-adjustAmount" name="adjustAmount" data-cy="adjustAmount" type="text" />
              <ValidatedField
                label="Discount Percentage"
                id="bill-discountPercentage"
                name="discountPercentage"
                data-cy="discountPercentage"
                type="text"
              />
              <ValidatedField label="Paid By" id="bill-paidBy" name="paidBy" data-cy="paidBy" type="text" />
              <ValidatedField
                label="Create Date Time"
                id="bill-createDateTime"
                name="createDateTime"
                data-cy="createDateTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="bill-hotel" name="hotel" data-cy="hotel" label="Hotel" type="select">
                <option value="" key="0" />
                {hotels
                  ? hotels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bill" replace color="info">
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

export default BillUpdate;
