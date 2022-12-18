import { useState } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Todo from './Todo'
import AddTodo from './AddTodo';
import { List, Paper } from '@mui/material';

function App() {
  const [items, setItems] = useState([
    {
      id:'0',
      title:'Hello World 1',
      done: true
    },
    {
      id:'1',
      title:'Hello World 2',
      done: true
    }
  ]);

  const addItem = (item) => {
    item.id = "ID-" + items.length; // key를 위한 id
    item.done = false; // done 초기화
    setItems([...items, item]);  // 업데이트는 반드시 setItems로 하고 새 배열을 만들어야 한다.
    console.log("items : ", [...items, item]);
  }
  
  let todoItems = items.length > 0 && items.map((item)=><Todo item={item} key={item.id}/>);
  
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