from celery import Celery
from time import sleep

app = Celery('tasks',  backend='rpc://', broker='amqp://guest:guest@localhost:5672/product')

@app.task 
def reverse(text):
    sleep(5)
    return text[::-1]