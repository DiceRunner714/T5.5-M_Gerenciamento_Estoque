from fastapi import FastAPI
from view import empresa_routes

app = FastAPI()

app.include_router(empresa_routes.router)

from config.database import Base, engine
from model.empresa_orm import EmpresaORM

@app.on_event("startup")
def on_startup():
    Base.metadata.create_all(bind=engine)
