import React, { useState } from 'react';
import { Checkbox, IconButton, InputBase, ListItem, ListItemSecondaryAction, ListItemText } from '@mui/material';
import { DeleteOutlined } from '@mui/icons-material';

function Todo(props) {
  const [item, setItem] = useState(props.item);
  const deleteItem = props.deleteItem; // App에서 전달 받은 todo 삭제 함수

  // deleteEventHandler 작성
  const deleteEventHandler = () => {
    deleteItem(item);
  }

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

        <ListItemSecondaryAction>
          <IconButton aria-label='Delete Todo' onClick={deleteEventHandler}>
            <DeleteOutlined />
          </IconButton>
        </ListItemSecondaryAction>
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