import { useState } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Todo from './Todo'
import AddTodo from './AddTodo';
import { List, Paper } from '@mui/material';

function App() {
  const [items, setItems] = useState([]);

  // todo 추가
  const addItem = (item) => {
    item.id = "ID-" + items.length; // key를 위한 id
    item.done = false; // done 초기화
    setItems([...items, item]);  // 업데이트는 반드시 setItems로 하고 새 배열을 만들어야 한다.
    console.log("items : ", [...items, item]);
  }

  // todo 삭제
  const deleteItem = (item) => {
    const newItems = items.filter(e => e.id !== item.id); // 삭제할 아이템
    setItems([...newItems]); // 삭제할 아이템을 제외한 아이템을 다시 배열에 저장
  }
  
  let todoItems = items.length > 0 && items.map((item)=><Todo item={item} key={item.id} deleteItem={deleteItem}/>);
  
  return (
    <div className="App container">
      <AddTodo addItem={addItem}/>
      <Paper style={{margin:16}}>
        <List>
          {todoItems}
        </List>
      </Paper>
      {/* <ListGroup style={{marginTop: 16}}>
        <ListGroupItem>{todoItems}</ListGroupItem>
      </ListGroup> */}
    </div>
  );
}

export default App;