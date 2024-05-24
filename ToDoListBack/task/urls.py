from django.urls import path
from .models import *
from .views import *

urlpatterns = [
    path('add_tag/', add_tag),
    path('edit_tag/', edit_tag),
    path('delete_tag/', delete_tag),
    path('get_tags/', get_tags),
    path('add_task/', add_task),
    path('edit_task/', edit_task),
    path('get_tasks/', get_tasks),
    path('delete_task/', delete_task),
]
