import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHotel } from 'app/shared/model/hotel.model';
import { getEntity, updateEntity, createEntity, reset } from './hotel.reducer';

export const HotelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hotelEntity = useAppSelector(state => state.hotel.entity);
  const loading = useAppSelector(state => state.hotel.loading);
  const updating = useAppSelector(state => state.hotel.updating);
  const updateSuccess = useAppSelector(state => state.hotel.updateSuccess);

  const handleClose = () => {
    navigate('/hotel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...hotelEntity,
      ...values,
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
          ...hotelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hotelInvoiceApp.hotel.home.createOrEditLabel" data-cy="HotelCreateUpdateHeading">
            <Translate contentKey="hotelInvoiceApp.hotel.home.createOrEditLabel">Create or edit a Hotel</Translate>
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
                  id="hotel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Name"
                id="hotel-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label="Address" id="hotel-address" name="address" data-cy="address" type="text" />
              <ValidatedField label="Contact" id="hotel-contact" name="contact" data-cy="contact" type="text" />
              <ValidatedField label="Owner Name" id="hotel-ownerName" name="ownerName" data-cy="ownerName" type="text" />
              <ValidatedField label="Owner Contact" id="hotel-ownerContact" name="ownerContact" data-cy="ownerContact" type="text" />
              <ValidatedField label="Gst No" id="hotel-gstNo" name="gstNo" data-cy="gstNo" type="text" />
              <ValidatedField label="Is Veg" id="hotel-isVeg" name="isVeg" data-cy="isVeg" check type="checkbox" />
              <ValidatedBlobField
                label="Logo"
                id="hotel-logo"
                name="logo"
                data-cy="logo"
                openActionLabel={translate('entity.action.open')}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hotel" replace color="info">
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

export default HotelUpdate;
