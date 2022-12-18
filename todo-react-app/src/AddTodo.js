import React, { useState } from 'react';
import { Button, Input, InputGroup } from 'reactstrap';

function AddTodo(props) {
  // 사용자의 입력을 저장할 오브젝트
  const [item, setItem] = useState({title:""});
  // App.js에서 넘겨준 addItem 함수
  const addItem = props.addItem;

  // onButtonClick 함수
  const onButtonClick = () => {
    addItem(item); // additem 함수 사용
    setItem({title: ""}); // todo 추가 후 input 빈 문자열로 초기화
  }

  // onInputChange 함수
  const onInputChange = (e) => {
    setItem({title : e.target.value});
    console.log(item);
  };

  // enterKeyEventHandler 함수
  const enterKeyEventHandler = (e) => {
    if(e.key === 'Enter') {
      onButtonClick();
    }
  }

  return (
    <div>
      <InputGroup size="lg" style={{marginTop: 20}}>
        {/* onInputChange 함수, enterKeyEventHandler 함수 연결 */}
        <Input placeholder='Add To here' onChange={onInputChange} onKeyPress={enterKeyEventHandler} value={item.title} />
        {/* onButtonClick 함수 연결 */}
        <Button outline style={{width: '10%'}} onClick={onButtonClick}>
          add
        </Button>
      </InputGroup>
    </div>
  );
}

export default AddTodo;