import { useEffect, useState } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Todo from './todo/Todo'
import AddTodo from './todo/AddTodo';
import { AppBar, Button, Container, Grid, List, Paper, Toolbar, Typography } from '@mui/material';
import { call, signout } from './util/ApiService';

function App() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  // todo item 조회
  // Effect 훅을 사용하여 무한 루프 방지
  useEffect(()=>{
    call('/todo', 'GET', null)
    .then((response) => {
      setItems(response.data);
      setLoading(false);
    });
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
  let navigationBar = (
    <AppBar position='static'>
      <Toolbar>
        <Grid justifyContent={"space-between"} container>
          <Grid item>
            <Typography variant='h6'>오늘의 할일</Typography>
          </Grid>
          <Grid item>
            <Button color='inherit' raised='true' onClick={signout}>로그아웃</Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );
  
  // 로딩중이 아닐 때 렌더링할 부분
  let todoListPage = (
    <div>
      {navigationBar}
      <Container maxwidth='md'>
        <AddTodo addItem={addItem}/>
        <Paper style={{margin:16}}>
          <List>
            {todoItems}
          </List>
        </Paper>
      </Container>
    </div>
  );

  // 로딩중일 때 렌더링 할 부분
  let loadingPage = <Container style={{marginTop: '30%'}}><h1> Loading .. </h1></Container>
  let content = loadingPage;
  if(!loading) {
    // 로딩중이 아니면 todoListPage 선택
    content = todoListPage;
  }
  
  return (
  <div className="App">
    {content}
  </div>
  );
}

export default App;