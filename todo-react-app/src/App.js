import { Paper } from '@mui/material';
import { useState } from 'react';
import { List, ListGroup, ListGroupItem } from 'reactstrap';
import './App.css';
import Todo from './Todo'
import 'bootstrap/dist/css/bootstrap.min.css';

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
  //let todoItems = items.length > 0 && items.map((item)=><Todo item={item} key={item.id}/>);
  let todoItems;
  if(items.length > 0) {
    todoItems = items.map((item)=>(<Todo item={item} key={item.id}/>));
  }
  return (
    <div className="App">
      <Paper style={{margin:16}}>
        <List>
          {todoItems}
        </List>
      </Paper>
    </div>
  );
}

export default App;