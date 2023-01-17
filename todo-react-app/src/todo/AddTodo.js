import { Button, Grid, TextField } from '@mui/material';
import React, { useState } from 'react';

function AddTodo(props) {
  const [item, setItem] = useState({title:""}); // 사용자의 입력을 저장할 오브젝트
  const addItem = props.addItem; // App.js에서 넘겨준 addItem 함수

  // onButtonClick 함수
  const onButtonClick = () => {
    addItem(item); // additem 함수 사용
    setItem({title: ""}); // todo 추가 후 input 빈 문자열로 초기화
  }

  // onInputChange 함수
  const onInputChange = (e) => {
    setItem({title : e.target.value});
    //console.log(item);
  };

  // enterKeyEventHandler 함수
  const enterKeyEventHandler = (e) => {
    if(e.key === 'Enter') {
      onButtonClick();
    }
  }

  return (
    <div style={{margin: 16}}>
      <Grid container>
        <Grid md={11} item style={{paddingRight: 16}}>
          <TextField
            placeholder="Add Todo here"
            fullWidth={true}
            onChange={onInputChange}
            value={item.title}
            onKeyPress={enterKeyEventHandler}
          />
        </Grid>
        <Grid md={1} item>
          <Button
            style={{height:'100%'}}
            fullWidth={true}
            color="secondary"
            variant="outlined"
            onClick={onButtonClick}
          >
            add
          </Button>
        </Grid>
      </Grid>
    </div>
  );
}

export default AddTodo;