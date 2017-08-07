from random import randint

# This is the only file you must implement

# This file will be imported from the main code. The PhysicalMemory class
# will be instantiated with the algorithm received from the input. You may edit
# this file as you which

# NOTE: there may be methods you don't need to modify, you must decide what
# you need...

class PhysicalMemory:
  ALGORITHM_AGING_NBITS = 8
  """How many bits to use for the Aging algorithm"""

  def __init__(self, algorithm):
    assert algorithm in {"fifo", "lru", "nru", "aging", "second-chance", "belady"}
    self.algorithm = algorithm;

    if (algorithm == "fifo"):
      self.implementation = Fifo()
    elif (algorithm == "lru"):
      self.implementation = LRU()
    elif (algorithm == "nru"):
      self.implementation = NRU()
    elif (algorithm == "aging"):
      self.implementation = Aging()
    elif (algorithm == "second-chance"):
      self.implementation = SecondChance()
    elif (algorithm == "belady"):
      self.implementation = Belady()

  def put(self, frameId):
    """Allocates this frameId for some page"""
    # Notice that in the physical memory we don't care about the pageId, we only
    # care about the fact we were requested to allocate a certain frameId
    self.implementation.put(frameId)

  def evict(self):
    """Deallocates a frame from the physical memory and returns its frameId"""
    # You may assume the physical memory is FULL so we need space!
    # Your code must decide which frame to return, according to the algorithm
    return self.implementation.evict()

  def clock(self):
    """The amount of time we set for the clock has passed, so this is called"""
    # Clear the reference bits (and/or whatever else you think you must do...)
    self.implementation.clock()

  def access(self, frameId, isWrite):
    """A frameId was accessed for read/write (if write, isWrite=True)"""
    self.implementation.access(frameId, isWrite)

#First In First Out
class Fifo:
  def __init__(self):
    self.frames = []

  def put(self, frameId):
    self.frames.append(frameId)

  def evict(self):
    if(len(self.frames) > 0):
      return int(self.frames.pop(0))
    return 0

  def clock(self):
    pass

  def access(self, frameId, isWrite):
    pass

#Least Recent Used
class LRU:
  '''def __init__(self):
    self.frames = {}

  def put(self, frameId):
    self.frames[frameId] = 1

  def evict(self):
    if(len(self.frames) > 0):
      min_frame = self.frames.keys()[0]

      for frame in self.frames.keys():
        if self.frames[frame] < self.frames[min_frame]:
          min_frame = frame

      return self.frames.pop(min_frame)
    return 0

  def clock(self):
    pass

  def access(self, frameId, isWrite):
    self.frames[frameId] += 1'''
  def __init__(self):
    self.frames = []

  def put(self, frameId):
    self.frames.append(frameId)

  def evict(self):
    if(len(self.frames) > 0):
      return self.frames.pop(0)
    return 0

  def clock(self):
    pass

  def access(self, frameId, isWrite):
    self.frames.remove(frameId)
    self.frames.append(frameId)

#Not Recently Used
class NRU:
  def __init__(self):
    self.frames = []

  def put(self, frameId):
    self.frames.append([frameId, 1, 1])

  def evict(self):
    if(len(self.frames) > 0):
      self.frames.sort(key=lambda pf: pf[2])
      self.frames.sort(key=lambda pf: pf[1])

      return self.frames.pop(0)[0]
    return 0

  def clock(self):
    for frame in self.frames:
      frame[1] = 0

  def access(self, frameId, isWrite):
    for frame in self.frames:
      if frame[0] == frameId:
        frame[1] = 1
        frame[2] = isWrite

#Aging
class Aging:
  def __init__(self):
    self.page_frames = []

  def put(self, frameId):
    self.page_frames.append([frameId, 0])

  def evict(self):
    self.page_frames.sort(key=lambda pf: pf[1])

    return self.page_frames.pop(0)[0]

  def clock(self):
    for p_frame in self.page_frames:
      p_frame[1] >>= 1

  def access(self, frameId, isWrite):
    for p_frame in self.page_frames:
      if p_frame[0] == frameId:
        p_frame[1] >>= 1
        p_frame[1] |= 256

#Second Chance
class SecondChance:
  def __init__(self):
    self.frames = []

  def put(self, frameId):
    self.frames.append([frameId, 1])

  def evict(self):
    pageFrame = self.frames.pop(0)

    while pageFrame[1]:
      self.frames.append([pageFrame[0], 0])
      pageFrame = self.frames.pop(0)

    return pageFrame[0]

  def clock(self):
    pass
  
  def access(self, frameId, isWrite):
    for f_id in self.frames:
      if frameId == f_id[0]:
        f_id[1] = 1

#Belady
class Belady:
  def __init__(self):
    self.frames = {}

  def put(self, frameId):
    label = randint(0,10000)
    self.frames[frameId] = label

  def evict(self):
    if(len(self.frames) > 0):
      highest_label = 0
      frame_id_highest_label = 0
      for frameId in self.frames:
        if(self.frames[frameId] > highest_label):
          highest_label = self.frames[frameId]
          frame_id_highest_label = frameId
    return frame_id_highest_label

  def clock(self):
    pass

  def access(self, frameId, isWrite):
    pass