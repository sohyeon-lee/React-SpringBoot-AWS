import { useEffect, useState } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Todo from './todo/Todo'
import AddTodo from './todo/AddTodo';
import { List, Paper } from '@mui/material';
import { call } from './util/ApiService';

function App() {
  const [items, setItems] = useState([]);

  // todo item 조회
  // Effect 훅을 사용하여 무한 루프 방지
  useEffect(()=>{
    call('/todo', 'GET', null)
    .then((response) => setItems(response.data));
  }, []);
  
  // todo item 추가
  const addItem = (item) => {
    call('/todo', 'POST', item)
    .then((response) => setItems(response.data));
  }
  
  // todo item 삭제
  const deleteItem = (item) => {
    call('/todo', 'DELETE', item)
    .then((response) => setItems(response.data));
  }
  
  // todo item 수정
  const editItem = (item) => {
    call('/todo', 'PUT', item)
    .then((response) => setItems(response.data));
  }
  
  let todoItems = items.length > 0 && items.map((item)=><Todo item={item} key={item.id} deleteItem={deleteItem} editItem={editItem}/>);
  
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