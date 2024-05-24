from rest_framework.decorators import api_view
from .serializers import TagSerializer, TaskSerializer, GetTaskSerializer, GetTagSerializer
from .models import *
from rest_framework.response import Response
from rest_framework import status


@api_view(['POST'])
def add_tag(request):
    serializer = TagSerializer(data=request.data)
    if serializer.is_valid():
        title = serializer.validated_data['title']
        Tag.objects.create(user=request.user, title=title)
        return Response({'message': 'برچسب با موفقیت ایجاد شد.'}, status.HTTP_200_OK)
    return Response({'error': serializer.errors}, status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def edit_tag(request):
    serializer = TagSerializer(data=request.data)
    if serializer.is_valid():
        try:
            tag = Tag.objects.get(id=serializer.validated_data['id'])
        except:
            return Response({'error':'برچسب پیدا نشد.'},status.HTTP_404_NOT_FOUND)
        tag.title = serializer.validated_data['title']
        try:
            tag.save()
        except:
            return Response({'error': 'عنوان برچسب تکراری است.'}, status.HTTP_409_CONFLICT)
        return Response({'message': 'برچسب با موفقیت ویرایش شد.'}, status.HTTP_200_OK)
    return Response({'error': serializer.errors}, status.HTTP_400_BAD_REQUEST)


@api_view(['DELETE'])
def delete_tag(request):
    serializer = TagSerializer(data=request.data)
    if serializer.is_valid():
        id = serializer.validated_data['id']
        try:
            Tag.objects.get(id=id).delete()
            return Response({'message': 'برچسب با موفقیت حذف شد.'}, status.HTTP_200_OK)
        except:
            return Response({'error':'برچسب پیدا نشد.'}, status.HTTP_404_NOT_FOUND)
    return Response({'error': serializer.errors}, status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
def get_tags(request):
    tags = Tag.objects.filter(user=request.user)
    serializer = GetTagSerializer(instance=tags, many=True)
    return Response(serializer.data, status.HTTP_200_OK)

@api_view(['POST'])
def add_task(request):
    serializer = TaskSerializer(data=request.data)
    if serializer.is_valid():
        user = request.user
        title = serializer.validated_data['title']
        description = serializer.validated_data['description']
        due_date = serializer.validated_data['due_date']
        priority = serializer.validated_data['priority']
        tags = serializer.validated_data['tags']
        task = Task.objects.create(user=user, title=title, description=description, due_date=due_date, priority=priority)
        for tag in tags:
            t = Tag.objects.get(id=tag['id'])
            task.tags.add(t)
        task.save()
        return Response({'message': 'وظیفه با موفقیت ایجاد شد.'}, status.HTTP_200_OK)
    return Response({'error': serializer.errors}, status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def edit_task(request):
    serializer = TaskSerializer(data=request.data)
    if serializer.is_valid():
        task = Task.objects.get(id=serializer.validated_data['id'])
        if "title" in serializer.validated_data:
            task.title = serializer.validated_data['title']
        if "description" in serializer.validated_data:
            task.description = serializer.validated_data['description']
        if "due_date" in serializer.validated_data:
            task.due_date = serializer.validated_data['due_date']
        if "is_completed" in serializer.validated_data:
            task.is_completed = serializer.validated_data['is_completed']
        if "priority" in serializer.validated_data:
            task.priority = serializer.validated_data['priority']

        if "tags" in serializer.validated_data:
            new_tags = [t['id'] for t in serializer.validated_data['tags']]
        else:
            new_tags = []
        old_tags = [t.id for t in task.tags.all()]

        for id in old_tags:
            if id not in new_tags:
                tag = Tag.objects.get(id=id)
                task.tags.remove(tag)
        for id in new_tags:
            if id not in old_tags:
                tag = Tag.objects.get(id=id)
                task.tags.add(tag)
        task.save()
        return Response({'message': 'وظیفه با موفقیت ویرایش شد.'}, status.HTTP_200_OK)

    return Response({'error': serializer.errors}, status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def get_tasks(request):
    serializer = TaskSerializer(data=request.data)
    user = request.user
    if serializer.is_valid():
        tasks = Task.objects.filter(user=user)

        if 'title' in serializer.validated_data:
            title = serializer.validated_data['title']
            tasks = tasks.filter(title__icontains=title)

        if 'description' in serializer.validated_data:
            description = serializer.validated_data['description']
            tasks = tasks.filter(description__icontains=description)

        if 'description' in serializer.validated_data:
            description = serializer.validated_data['description']
            tasks = tasks.filter(description__icontains=description)

        if 'due_date' in serializer.validated_data:
            due_date = serializer.validated_data['due_date']
            tasks = tasks.filter(due_date__gte=due_date)

        if 'due_date_end' in serializer.validated_data:
            due_date_end = serializer.validated_data['due_date_end']
            tasks = tasks.filter(due_date__lte=due_date_end)
            
        if 'is_completed' in serializer.validated_data:
            is_completed = serializer.validated_data['is_completed']
            tasks = tasks.filter(is_completed=is_completed)

        if 'priority' in serializer.validated_data:
            priority = serializer.validated_data['priority']
            tasks = tasks.filter(priority=priority)

        if 'tags' in serializer.validated_data:
            tags = [t['id'] for t in serializer.validated_data['tags']]
            tasks = tasks.filter(tags__id__in=tags)

        res_serializer = GetTaskSerializer(instance=tasks, many=True)
        return Response(res_serializer.data, status.HTTP_200_OK)

    return Response({'error': serializer.errors}, status.HTTP_400_BAD_REQUEST)

@api_view(['DELETE'])
def delete_task(request):
    user = request.user
    serializer = TaskSerializer(data=request.data)
    if serializer.is_valid():
        task = Task.objects.get(user=user, id=serializer.validated_data['id'])
        task.delete()
        return Response({"message": "وظیفه با موفقیت حذف شد."}, status.HTTP_200_OK)
    return Response({'error': serializer.errors}, status.HTTP_400_BAD_REQUEST)