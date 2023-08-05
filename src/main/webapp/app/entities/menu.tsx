import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/bill-items">
        Bill Items
      </MenuItem>
      <MenuItem icon="asterisk" to="/bill">
        Bill
      </MenuItem>
      <MenuItem icon="asterisk" to="/menu">
        Menu
      </MenuItem>
      <MenuItem icon="asterisk" to="/hotel">
        Hotel
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
