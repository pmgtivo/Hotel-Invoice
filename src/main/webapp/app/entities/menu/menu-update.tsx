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
import { IMenu } from 'app/shared/model/menu.model';
import { getEntity, updateEntity, createEntity, reset } from './menu.reducer';

export const MenuUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hotels = useAppSelector(state => state.hotel.entities);
  const menuEntity = useAppSelector(state => state.menu.entity);
  const loading = useAppSelector(state => state.menu.loading);
  const updating = useAppSelector(state => state.menu.updating);
  const updateSuccess = useAppSelector(state => state.menu.updateSuccess);

  const handleClose = () => {
    navigate('/menu');
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
    const entity = {
      ...menuEntity,
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
      ? {}
      : {
          ...menuEntity,
          hotel: menuEntity?.hotel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hotelInvoiceApp.menu.home.createOrEditLabel" data-cy="MenuCreateUpdateHeading">
            <Translate contentKey="hotelInvoiceApp.menu.home.createOrEditLabel">Create or edit a Menu</Translate>
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
                  id="menu-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Name"
                id="menu-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label="Price"
                id="menu-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label="Description" id="menu-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Is Veg" id="menu-isVeg" name="isVeg" data-cy="isVeg" check type="checkbox" />
              <ValidatedField
                label="Item Available"
                id="menu-itemAvailable"
                name="itemAvailable"
                data-cy="itemAvailable"
                check
                type="checkbox"
              />
              <ValidatedField id="menu-hotel" name="hotel" data-cy="hotel" label="Hotel" type="select">
                <option value="" key="0" />
                {hotels
                  ? hotels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/menu" replace color="info">
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

export default MenuUpdate;
