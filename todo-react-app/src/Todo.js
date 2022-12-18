import React, { useState } from 'react';
import { Checkbox, InputBase, ListItem, ListItemText } from '@mui/material';

function Todo(props) {
  const [item, setItem] = useState(props.item);

  return (
    <>
      <ListItem>
        <Checkbox checked={item.done} />
        <ListItemText>
          <InputBase
            inputProps={{ 'aria-label': 'naked' }}
            type="text"
            id={item.id}
            name={item.id}
            value={item.title}
            multiline={true}
            fullWidth={true}
          />
        </ListItemText>
      </ListItem>

      {/* reactstrap */}
      {/* <ListGroupItem>
          <InputGroup>
            <Input type="checkbox" checked={item.done} readOnly/>
            <ListGroupItemText>
              <Input plaintext id={item.id} name={item.id} value={item.title} readOnly/>
            </ListGroupItemText>
          </InputGroup>
      </ListGroupItem> */}
    </>
  );
}

export default Todo;