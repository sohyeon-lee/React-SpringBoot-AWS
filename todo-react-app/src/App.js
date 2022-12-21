import { useEffect, useState } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Todo from './todo/Todo'
import AddTodo from './todo/AddTodo';
import { List, Paper } from '@mui/material';

function App() {
  const [items, setItems] = useState([]);

  // API를 이용해 리스트 초기화
  const serverPath = 'http://localhost:8080';
  // Effect 훅을 사용하여 무한 루프 방지
  useEffect(()=>{
    const requestOptions = {
      method: 'GET',
      Headers: {'Content-Type': 'application/json'},
    };
    fetch(`${serverPath}/todo`, requestOptions)
      .then((response) => response.json())
      .then(
        (response) => {setItems(response.data)},
        (error) => {}
      );
  }, []);
  
  // todo item 추가
  const addItem = (item) => {
    item.id = "ID-" + items.length; // key를 위한 id
    item.done = false; // done 초기화
    setItems([...items, item]); // 업데이트는 반드시 setItems로 하고 새 배열을 만들어야 한다.
    console.log("[addItem] items : ", [...items, item]);
  }
  
  // todo item 삭제
  const deleteItem = (item) => {
    const newItems = items.filter(e => e.id !== item.id); // 삭제할 아이템
    setItems([...newItems]); // 삭제할 아이템을 제외한 아이템을 다시 배열에 저장
    console.log("[deleteItem] items : ", [...items]);
  }
  
  // todo item 수정
  const editItem = () => {
    setItems([...items]); // 수정된 items로 새 배열로 초기화
    console.log("[editItem] items : ", [...items]);
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