import React, { useState } from 'react';
import { Checkbox, IconButton, InputBase, ListItem, ListItemSecondaryAction, ListItemText } from '@mui/material';
import { DeleteOutlined } from '@mui/icons-material';

function Todo(props) {
  const [item, setItem] = useState(props.item);
  const [readOnly, setReadOnly] = useState(true);
  const deleteItem = props.deleteItem;  // App에서 전달 받은 todo 삭제 함수
  const editItem = props.editItem;      // App에서 전달 받은 todo 수정 함수

  // deleteEventHandler 작성
  const deleteEventHandler = () => {
    deleteItem(item);
  }

  // title 클릭 시 실행할 함수 (readOnly false)
  const turnOffReadOnly = () => {
    setReadOnly(false);
  }

  // enter키를 누르면 readOnly 모드를 종료하는 함수 (readOnly true)
  const turnOnReadOnly = (e) => {
    if(e.key === 'Enter') {
      setReadOnly(true);
      // 이후 updateAPI와 연결하여 서버에 바뀐 아이템 저장 예정
    }
  }

  // title 변경을 위한 이벤트 핸들러 함수
  const editEventHandler = (e) => {
    item.title = e.target.value;
    editItem();
  }

  // checkbox 체크 변경을 위한 이벤트 핸들러 함수
  const checkboxEventHandler = (e) => {
    item.done = e.target.checked;
    editItem();
  }

  return (
    <>
      <ListItem>
        <Checkbox checked={item.done} 
          onChange={checkboxEventHandler} // checkbox 체크 변경 이벤트 핸들러 함수 연결
        /> 
        
        <ListItemText>
          <InputBase
            inputProps={{ 
              'aria-label': 'naked',
              readOnly: readOnly,
            }}
            onClick={turnOffReadOnly}   // 마우스 클릭
            onKeyDown={turnOnReadOnly}  // enter 키
            onChange={editEventHandler} // title 변경 이벤트 핸들러 함수 연결
            type="text"
            id={item.id}
            name={item.id}
            value={item.title}
            multiline={true}
            fullWidth={true}
          />
        </ListItemText>

        <ListItemSecondaryAction>
          {/* deleteEventHandler 함수 연결 */}
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